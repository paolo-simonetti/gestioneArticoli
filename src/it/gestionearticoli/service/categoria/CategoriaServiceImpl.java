package it.gestionearticoli.service.categoria;

import java.sql.Connection;
import java.util.TreeSet;

import it.gestionearticoli.connection.MyConnection;
import it.gestionearticoli.dao.Constants;
import it.gestionearticoli.dao.categoria.CategoriaDAO;
import it.gestionearticoli.model.Categoria;

public class CategoriaServiceImpl implements CategoriaService {
	
	private CategoriaDAO categoriaDao;
	
	@Override
	public void setCategoriaDao(CategoriaDAO categoriaDao) {
		this.categoriaDao=categoriaDao;
	}

	@Override
	public TreeSet<Categoria> listAll() throws Exception {
		TreeSet<Categoria> result = new TreeSet<>();
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			categoriaDao.setConnection(connection);
			result = categoriaDao.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Categoria trovaTramiteId(Long idCategoria) throws Exception {
		Categoria result=new Categoria();
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			categoriaDao.setConnection(connection);
			result = categoriaDao.get(idCategoria);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int aggiorna(Categoria input) throws Exception {
		int result=-1;
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			categoriaDao.setConnection(connection);
			result = categoriaDao.update(input);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Long inserisciNuovo(Categoria input) throws Exception {
		Long result = 0L;
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			categoriaDao.setConnection(connection);
			result = categoriaDao.insert(input);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int rimuovi(Categoria input) throws Exception {
		int result=-1;
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			categoriaDao.setConnection(connection);
			result = categoriaDao.delete(input);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	@Override
	public boolean sincronizzaElencoArticoli(Categoria input) throws Exception {
		boolean result=false;
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			categoriaDao.setConnection(connection);
			result = categoriaDao.synchroElencoArticoli(input);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public TreeSet<Categoria> ricercaCategoria(TreeSet<String> input) throws Exception {
		TreeSet<Categoria> result=null;
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			categoriaDao.setConnection(connection);
			result = categoriaDao.searchCategoria(input);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

}
