package fastcampus.ad.migration.application.legacyad;

public interface LegacyConverter<Legacy, Recent> {

  Recent convert(Legacy legacy);
}
