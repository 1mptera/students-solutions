import com.sun.net.httpserver.HttpServer;

import models.Account;
import utils.AccountPageGenerator;
import utils.GreetingPageGenerator;
import utils.PageGenerator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

import utils.MessageWriter;

public class MakaoBank {
  public static void main(String[] args) throws IOException {
    MakaoBank application = new MakaoBank();
    application.run();
  }

  private void run() throws IOException {
    InetSocketAddress address = new InetSocketAddress(8000);
    HttpServer httpServer = HttpServer.create(address, 0);

    httpServer.createContext("/", (exchange) -> {
      URI requestURI = exchange.getRequestURI();
      String path = requestURI.getPath();

      PageGenerator pageGenerator = new GreetingPageGenerator();

      if(path.equals("/account")) {
        Account account = new Account("1234", "ashal", 3000);
        pageGenerator = new AccountPageGenerator(account);
      }

      String content = pageGenerator.html();

      // 출력
      MessageWriter messageWriter = new MessageWriter(exchange);
      messageWriter.write(content);
    });
    httpServer.start();

    System.out.print("http://localhost:8000");
  }

}