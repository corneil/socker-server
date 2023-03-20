package com.github.corneil.socketserver

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.logging.LogManager
import java.util.logging.Logger

fun initLogging() {
  val file = File("logging.properties")
  val stream = (if (file.isFile) file.inputStream() else
    ClassLoader.getSystemClassLoader().getResourceAsStream("logging.properties")
      ) ?: throw RuntimeException("Cannot find logging.properties")
  LogManager.getLogManager().readConfiguration(stream);
}

fun runClient(socket: Socket) {
  val echoService = EchoService()
  val logger = Logger.getLogger("socket-server")
  logger.info("Accepted:${socket.remoteSocketAddress}:${socket.port}")
  try {
    val input = BufferedReader(InputStreamReader(socket.getInputStream()))
    val output = PrintWriter(socket.getOutputStream(), true)
    while (true) {
      val message = input.readLine()
      if (message != null) {
        logger.fine("Received:$message")
        val result = echoService.echo(message)
        logger.fine("Sending:$result")
        output.println(result)
      } else {
        logger.info("Response:null")
        socket.close()
        break;
      }
    }
  } catch (x: Throwable) {
    logger.info("Exception:${x}")
  }
}

fun main(args: Array<String>) {
  initLogging()
  val logger = Logger.getLogger("socket-server")
  if (args.isNotEmpty()) {
    System.setProperty("app.port", args[0])
  }
  val port = System.getProperty("app.port", "9000").toInt()
  val socketServer = ServerSocket(port)
  while (true) {
    logger.info("Waiting for connection on ${socketServer.inetAddress}:${socketServer.localPort}")
    val client = socketServer.accept();
    CoroutineScope(Dispatchers.IO).async {
      runClient(client)
    }
  }
}
