package m2m.bi.proxy.dao;

import java.util.Set;

public interface LinkDAO {

	void create(long id_Passeggero, long id_Volo);

	Set<Long> read_by_id_Passeggero(long id_Passeggero);

	Set<Long> read_by_id_Volo(long id_Volo);

	boolean delete(long id_Passeggero, long id_Volo);

	boolean delete_by_id_Passeggero(long id_Passeggero);

	boolean delete_by_id_Volo(long id_Volo);

	boolean createTable();

	boolean dropTable();

}