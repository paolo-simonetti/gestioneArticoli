package it.gestionearticoli.service.utente;

import java.sql.Connection;
import java.util.TreeSet;

import it.gestionearticoli.connection.MyConnection;
import it.gestionearticoli.dao.Constants;
import it.gestionearticoli.dao.utente.UtenteDAO;
import it.gestionearticoli.model.Utente;

public class UtenteServiceImpl implements UtenteService {

	private UtenteDAO utenteDao;
	
	@Override
	public void setUtenteDao(UtenteDAO utenteDao) {
		this.utenteDao = utenteDao;
	}

	@Override
	public TreeSet<Utente> listAll() throws Exception {
		TreeSet<Utente> result = new TreeSet<>();
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			utenteDao.setConnection(connection);
			result = utenteDao.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Utente trovaTramiteId(Long idInput) throws Exception {
		Utente result=new Utente();
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			utenteDao.setConnection(connection);
			result = utenteDao.get(idInput);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int aggiorna(Utente input) throws Exception {
		int result=-1;
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			utenteDao.setConnection(connection);
			result = utenteDao.update(input);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Long inserisciNuovo(Utente input) throws Exception {
		Long result = 0L;
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			utenteDao.setConnection(connection);
			result = utenteDao.insert(input);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int rimuovi(Utente input) throws Exception {
		int result=-1;
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			utenteDao.setConnection(connection);
			result = utenteDao.delete(input);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public TreeSet<Utente> findByExample(Utente input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
