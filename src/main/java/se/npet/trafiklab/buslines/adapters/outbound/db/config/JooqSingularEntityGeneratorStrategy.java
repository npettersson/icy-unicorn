package se.npet.trafiklab.buslines.adapters.outbound.db.config;
import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

public class JooqSingularEntityGeneratorStrategy extends DefaultGeneratorStrategy {

  @Override
  public String getJavaClassName(Definition definition, Mode mode) {
    final String defaultName = super.getJavaClassName(definition, mode);
    final String pojoSuffix = "Entity";
    if (mode == Mode.POJO) {
      if (defaultName.endsWith("ies")) {
        return defaultName.substring(0, defaultName.length() - 3) + "y" + pojoSuffix;
      } else if (defaultName.endsWith("s")) {
        return defaultName.substring(0, defaultName.length() - 1) + pojoSuffix;
      } else {
        return defaultName + pojoSuffix;
      }
    }
    return defaultName;
  }
}
