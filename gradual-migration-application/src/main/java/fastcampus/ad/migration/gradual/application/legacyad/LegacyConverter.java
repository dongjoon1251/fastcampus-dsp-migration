package fastcampus.ad.migration.gradual.application.legacyad;

public interface LegacyConverter<Legacy, Recent> {

  Recent convert(Legacy legacy);
}
