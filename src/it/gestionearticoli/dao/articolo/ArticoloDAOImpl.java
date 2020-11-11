package it.gestionearticoli.dao.articolo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import it.gestionearticoli.dao.AbstractMySQLDAO;
import it.gestionearticoli.dao.categoria.CategoriaDAO;
import it.gestionearticoli.dao.categoria.CategoriaDAOImpl;
import it.gestionearticoli.model.Articolo;
import it.gestionearticoli.model.Categoria;

public class ArticoloDAOImpl extends AbstractMySQLDAO implements ArticoloDAO {

	@Override
	public TreeSet<Articolo> list() throws Exception {
		if (isNotActive()) {
			return null;
		}

		TreeSet<Articolo> result = new TreeSet<Articolo>();
		Articolo articoloTemp = null;

		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery("select * from articolo");

			while (rs.next()) {
				articoloTemp = new Articolo();
				articoloTemp.setCodice(rs.getString("CODICE"));
				articoloTemp.setDescrizione(rs.getString("DESCRIZIONE"));
				articoloTemp.setPrezzo(rs.getInt("PREZZO"));
				articoloTemp.setId(rs.getLong("ID"));
				CategoriaDAO categoriaDAO=new CategoriaDAOImpl();
				categoriaDAO.setConnection(connection);
				if (categoriaDAO.get(rs.getLong("CATEGORIA_FK"))!=null) {
					articoloTemp.setCategoriaDiAfferenza(categoriaDAO.get(rs.getLong("CATEGORIA_FK"))); 					
				}
				result.add(articoloTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Articolo get(Long id) throws Exception {
		if (isNotActive()||id==null) {
			return null;
		} else {
			Articolo result=new Articolo();
			String query="SELECT * FROM articolo WHERE ID=?";
			try (PreparedStatement preparedStatement=connection.prepareStatement(query)) {
				preparedStatement.setLong(1,id);
				ResultSet resultSet=preparedStatement.executeQuery();
				if (resultSet.next()) {
					result.setId(resultSet.getLong("ID"));
					result.setCodice(resultSet.getString("CODICE"));
					result.setPrezzo(resultSet.getInt("PREZZO"));
					result.setDescrizione(resultSet.getString("DESCRIZIONE"));
					CategoriaDAO categoriaDAO=new CategoriaDAOImpl();
					categoriaDAO.setConnection(connection);
					result.setCategoriaDiAfferenza(categoriaDAO.get(resultSet.getLong("CATEGORIA_FK")));
				} else {
					result=null;
				}
			} catch(SQLException e) {
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return result;			
		}
	}

	@Override
	public int update(Articolo input) throws Exception {
		if (isNotActive()||input==null) {
			return -1;
		} else {
			int result=0;
			String query=null;
			if (input.getCategoriaDiAfferenza()!=null) {
				query="UPDATE articolo SET PREZZO=?,DESCRIZIONE=?,CODICE=?, CATEGORIA_FK=? WHERE ID=?";				
			} else {
				query="UPDATE articolo SET PREZZO=?,DESCRIZIONE=?,CODICE=? WHERE ID=?";
			}
			try (PreparedStatement preparedStatement=connection.prepareStatement(query)) {
				preparedStatement.setInt(1,input.getPrezzo());
				preparedStatement.setString(2,input.getDescrizione());
				preparedStatement.setString(3,input.getCodice());
				if(input.getCategoriaDiAfferenza()!=null) {
					preparedStatement.setLong(4,input.getCategoriaDiAfferenza().getIdCategoria());					
					preparedStatement.setLong(5,input.getId());

				} else {
					preparedStatement.setLong(4,input.getId());
				}
				result= preparedStatement.executeUpdate();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
	}

	@Override
	public Long insert(Articolo input) throws Exception {
		if (isNotActive() || input == null) {
			return -1L;
		}
		Long result = 0L;
		String query=null;
		if (input.getCategoriaDiAfferenza()!=null) {
			query="INSERT INTO articolo (PREZZO, DESCRIZIONE, CODICE, CATEGORIA_FK) VALUES (?, ?, ?, ?)";
		} else {
			query="INSERT INTO articolo (PREZZO, DESCRIZIONE, CODICE) VALUES (?, ?, ?)";
		}
		try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, input.getPrezzo());
			ps.setString(2, input.getDescrizione());
			ps.setString(3, input.getCodice());
			if (input.getCategoriaDiAfferenza()!=null) {
				ps.setLong(4,input.getCategoriaDiAfferenza().getIdCategoria());				
			} 
			ps.executeUpdate();
			ResultSet resultSet=ps.getGeneratedKeys();
			if (resultSet.next()) {
				input.setId(resultSet.getLong(1));
				result=input.getId();
			} else {
				System.err.println("Generated Keys non trovate per insertArticolo");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Articolo input) throws Exception {
		if (isNotActive()||input==null) {
			return -1;
		} else {
			int result=0;
			String query="DELETE FROM articolo WHERE ID=?";
			try (PreparedStatement preparedStatement=connection.prepareStatement(query)) {
				preparedStatement.setLong(1,input.getId());
				result= preparedStatement.executeUpdate();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
	}
	
	public TreeSet<Articolo> listArticoliCategoria(Long idCategoriaInput) throws Exception {
		if(isNotActive()) {
			return null;
		} else if(idCategoriaInput==0L||idCategoriaInput==null) {
			TreeSet<Articolo> result=new TreeSet<Articolo>();
			String query="SELECT* FROM articolo WHERE CATEGORIA_FK IS NULL";
			try(Statement statement=connection.createStatement()) {
				ResultSet resultSet=statement.executeQuery(query); 
				Articolo articoloTemp=null;
				while (resultSet.next()) {
					articoloTemp = new Articolo();
					articoloTemp.setCodice(resultSet.getString("CODICE"));
					articoloTemp.setDescrizione(resultSet.getString("DESCRIZIONE"));
					articoloTemp.setPrezzo(resultSet.getInt("PREZZO"));
					articoloTemp.setId(resultSet.getLong("ID"));
					result.add(articoloTemp);					
				}
			}
			return result;
		} else {
			TreeSet<Articolo> result=new TreeSet<Articolo>();
			String query="SELECT* FROM articolo WHERE CATEGORIA_FK=?";
			try(PreparedStatement preparedStatement=connection.prepareStatement(query)) {
				preparedStatement.setLong(1,idCategoriaInput);
				ResultSet resultSet=preparedStatement.executeQuery(); 
				Articolo articoloTemp=null;
				while (resultSet.next()) {
					articoloTemp = new Articolo();
					articoloTemp.setCodice(resultSet.getString("CODICE"));
					articoloTemp.setDescrizione(resultSet.getString("DESCRIZIONE"));
					articoloTemp.setPrezzo(resultSet.getInt("PREZZO"));
					articoloTemp.setId(resultSet.getLong("ID"));
					CategoriaDAO categoriaDAO=new CategoriaDAOImpl();
					categoriaDAO.setConnection(connection);
					articoloTemp.setCategoriaDiAfferenza(categoriaDAO.get(resultSet.getLong("CATEGORIA_FK")));
					result.add(articoloTemp);					
				}
			}
			return result;
		}
		
	}
	
	@Override
	public TreeSet<Articolo> searchArticolo(TreeMap<String,TreeSet<String>> input) throws Exception {
		if (isNotActive()) {
			return null;
		} else {
			TreeSet<Articolo> result=null;
			// Costruisco il pezzo della query relativo al codice solo se, nella mappa in input, c'è effettivamente la chiave "codice"
			String query="SELECT A.ID,A.PREZZO,A.DESCRIZIONE,A.CODICE,C.id_categoria,C.nome_categoria FROM articolo A "
					+ "LEFT OUTER JOIN categoria C ON A.CATEGORIA_FK=C.id_categoria WHERE ";
			/*sono in empasse con la costruzione della query. Il modo di risolverla è usare il keySet per sapere quali sono le chiavi 
			effettivamente presenti. Pro-tip: rinominati le chiavi esattamente come le colonne della tabella
			*/
			Set<String> campiDiRicercaNonNulli= input.keySet();
			boolean presenzaDelPrezzo=false;
			for (String campo:campiDiRicercaNonNulli) {
				if (!campo.equals("PREZZO")) { //il pezzo di query relativo al prezzo lo scrivo diversamente (cioè senza il 'like')
					String pezzoQueryCampo=new String("");
					for (String s:input.get(campo)) {
						pezzoQueryCampo+=campo+" LIKE '%"+s+"%' AND ";
					}
					query+=pezzoQueryCampo;
				} else {
					presenzaDelPrezzo=true;
				}		
			}
			/*A questo punto, se nei campi di input non c'è solo il prezzo, ho una (lunghissima) query che termina con un " AND ", derivante
			dal ciclo più interno eseguito precedentemente. Se invece c'è solo il prezzo come input, ho solo la query originale scritta in alto.
			Quindi, se negli input di ricerca c'è anche il prezzo, costruisco il pezzo di query ad esso relativo e lo concateno al precedente;
			altrimenti, devo togliere quell'AND. Intanto, costruisco il pezzo di query relativo al prezzo.  */
			if (presenzaDelPrezzo) {
				String pezzoQueryPrezzo="PREZZO="+input.get("PREZZO");
				query+=pezzoQueryPrezzo;
			} else {
				query=query.substring(0,query.length()-"AND ".length());
			}
			// Ora la query è pronta.
			try(Statement statement=connection.createStatement()) {
				ResultSet resultSet=statement.executeQuery(query);
				Articolo articoloTemp=null;
				Categoria categoriaTemp=null;
				result=new TreeSet<>();
				while (resultSet.next()) {
					articoloTemp=new Articolo();
					articoloTemp.setId(resultSet.getLong("ID"));
					articoloTemp.setPrezzo(resultSet.getInt("PREZZO"));
					articoloTemp.setDescrizione(resultSet.getString("DESCRIZIONE"));
					articoloTemp.setCodice(resultSet.getString("CODICE"));
					categoriaTemp=new Categoria(resultSet.getString("nome_categoria"));
					categoriaTemp.setIdCategoria(resultSet.getLong("id_categoria"));
					articoloTemp.setCategoriaDiAfferenza(categoriaTemp);
					result.add(articoloTemp);
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return result;			
		}
	}

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
