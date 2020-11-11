package it.gestionearticoli.service.articolo;

import java.sql.Connection;
import java.util.TreeMap;
import java.util.TreeSet;

import it.gestionearticoli.connection.MyConnection;
import it.gestionearticoli.dao.Constants;
import it.gestionearticoli.dao.articolo.ArticoloDAO;
import it.gestionearticoli.model.Articolo;

public class ArticoloServiceImpl implements ArticoloService {

	private ArticoloDAO articoloDao;

	public void setArticoloDao(ArticoloDAO articoloDao) {
		this.articoloDao = articoloDao;
	}

	@Override
	public TreeSet<Articolo> listAll() throws Exception {
		TreeSet<Articolo> result = new TreeSet<>();
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {

			// inietto la connection nel dao
			articoloDao.setConnection(connection);

			// eseguo quello che realmente devo fare
			result = articoloDao.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Articolo trovaTramiteId(Long idInput) throws Exception {
		Articolo result=new Articolo();
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			articoloDao.setConnection(connection);
			result = articoloDao.get(idInput);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int aggiorna(Articolo input) throws Exception {
		int result=-1;
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			articoloDao.setConnection(connection);
			result = articoloDao.update(input);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Long inserisciNuovo(Articolo input) throws Exception {
		Long result = 0L;
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			articoloDao.setConnection(connection);
			result = articoloDao.insert(input);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int rimuovi(Articolo input) throws Exception {
		int result=-1;
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			articoloDao.setConnection(connection);
			result = articoloDao.delete(input);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public TreeSet<Articolo> elencaArticoliCategoria(Long idCategoriaInput) throws Exception {
		TreeSet<Articolo> result=null;
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			articoloDao.setConnection(connection);
			result = articoloDao.listArticoliCategoria(idCategoriaInput); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	@Override
	public TreeSet<Articolo> ricercaArticolo(TreeMap<String,TreeSet<String>> input) throws Exception {
		TreeSet<Articolo> result=null;
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			articoloDao.setConnection(connection);
			result = articoloDao.searchArticolo(input); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;	
	}

}
