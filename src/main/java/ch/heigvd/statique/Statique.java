package ch.heigvd.statique;

import ch.heigvd.statique.commands.Build;
import ch.heigvd.statique.commands.Clean;
import ch.heigvd.statique.commands.Init;
import ch.heigvd.statique.commands.Serve;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IVersionProvider;
import picocli.CommandLine.Option;

/**
 * Classe principale qui regroupe les différentes commandes pour gérer le site statique
 */
@Command(
    name = "statique",
    description = "A brand new static site generator.",
    subcommands = {Init.class, Clean.class, Build.class, Serve.class},
    versionProvider = Statique.PropertiesVersionProvider.class)
public class Statique implements Callable<Integer> {

  /** Spécifie si la version est requise dans la commande **/
  @Option(names = {"-V", "--version"}, versionHelp = true, description = "Print version info and exit")
  boolean versionRequested;

  /**
   * Programme principal qui génère le site statique
   * @param args les arguments passés en ligne de commande
   */
  public static void main(String... args) {
    int exitCode = new CommandLine(new Statique()).execute(args);
    if (exitCode != 0) {
      System.exit(exitCode);
    }
  }

  /**
   * Appel de la commande désirée
   * @return 0 si tout s'est bien passé
   */
  @Override
  public Integer call() {
    CommandLine.usage(this, System.out);
    return 0;
  }

  static class PropertiesVersionProvider implements IVersionProvider {

    /**
     * Permet d'obtenir la version actuelle de l'application
     * @return la version de l'application
     * @throws Exception
     */
    public String[] getVersion() throws Exception {
      URL url = getClass().getResource("/version.txt");
      if (url == null) {
        return new String[]{"No version.txt file found in the classpath. Is examples.jar in the classpath?"};
      }
      Properties properties = new Properties();
      properties.load(url.openStream());
      return new String[]{
          properties.getProperty("application") + " " + properties.getProperty("version")
      };
    }
  }
}
