package telran.cars.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.cars.dto.ModelNameAmount;
import telran.cars.service.model.*;

public interface ModelRepo extends JpaRepository<Model, ModelYear> {
@Query(value="select c.model.modelYear.name as modelName " +
"from Car c, TradeDeal td " +
"where c.number = td.car.number " +
"group by c.model.modelYear.name " +
"having count(c) = (select max(count) " +
" from (select count(*) AS count " +
" from Car car, TradeDeal t " +
" where car.number = t.car.number " +
"group by car.model.modelYear.name))", nativeQuery=false) //just SQL query
	List<String> findMostSoldModelNames();
/*************************************************************/
@Query(value="select c.model.modelYear.name as name, count(c) as amount " +
	       "from Car c " +
	       "group by c.model.modelYear.name " +
	       "order by count(c) desc limit :nModels", nativeQuery=false)
List<ModelNameAmount> findMostPopularModelNames(int nModels);
/*************************************************************************/
@Query(value="select c.model.modelYear.name as name, count(c) as amount " +
	       "from Car c " +
	       "where c.carOwner.birthDate between :birthDate1 AND :birthDate2 " +
	       "group by c.model.modelYear.name " +
	       "order by amount DESC limit :nModels", nativeQuery=true)
List<ModelNameAmount> findPopularModelNameOwnerAges(int nModels,
		LocalDate birthDate1, LocalDate birthDate2);

}
