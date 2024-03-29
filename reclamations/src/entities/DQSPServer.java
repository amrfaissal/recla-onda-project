package entities;

public interface DQSPServer {

	public void addPassager(int idPassager, String sex, String nom,
			String mail, String adresse, String codePostale, String phone,
			String typeReclamateur, String numVol, String prov, String dest,
			String nationalite, String idAeroport);

	public int addReclamation(int idPassager, java.sql.Date date,
			String nomAeroport, String terminale, String nomService,
			String remarque, String descriptif, String theme, String typeReclamateur);

	public void addAction(String service, String theme, String descriptif, String idAeroport); 

	public java.util.List<String> listOfAirports();

}
