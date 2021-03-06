package ch.heigvd.statique.commands;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

/**
 * Commande qui permet de servir le site web
 */
@Command(name = "serve", description = "Serve a static site")
public class Serve implements Callable<Integer> {

  /** Le chemin des fichiers contenant le site à servir **/
  @Parameters(paramLabel = "SITE", description = "The site to serve")
  public Path site;

  /**
   * Appel de la commande
   * @return 0 si tout s'est bien passé
   */
  @Override
  public Integer call() {
    // Build the site
    new CommandLine(new Build()).execute(site.toString());

    // Serve the site
    Javalin.create(config -> {
      config.addStaticFiles(site.resolve("build").toAbsolutePath().toString(), Location.EXTERNAL);
    }).start(8080);

    return 0;
  }

}