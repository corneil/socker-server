package com.github.corneil.socketserver

import java.util.logging.Logger


class EchoService {
  companion object {
    private val logger = Logger.getLogger("socket-server")
  }

  fun echo(input: String): String {
    logger.info("echo:$input")
    return "echo:$input"
  }
}