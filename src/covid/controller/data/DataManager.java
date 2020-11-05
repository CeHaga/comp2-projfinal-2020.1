package covid.controller.data;

import covid.controller.api.APIReader;
import covid.controller.files.CacheManager;
import covid.enums.StatusCaso;
import covid.models.Medicao;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Carlos Bravo - cehaga@dcc.ufrj.br
 */
public class DataManager {
    private static DataManager dataManager;

    private DataManager(){

    }
    
    public static DataManager getDataManager() {
        if(dataManager == null) dataManager = new DataManager();
        return dataManager;
    }

//    public List<Medicao> getMedicaoList(StatusCaso status, LocalDateTime startDate, LocalDateTime endDate){
//        CacheManager cacheManager = new CacheManager();
//        LocalDateTime missingStartDate = null;
//        List<Medicao> list = new ArrayList<>();
//        for(LocalDateTime date = startDate; !date.isAfter(endDate); date = date.plusDays(1)){
//            Medicao medicao = cacheManager.readFile(status, date);
//            if(medicao == null){
//                if(missingStartDate == null){
//                    missingStartDate = date;
//                }
//            }else{
//                if(missingStartDate != null){
//                    List<Medicao> missingMedicao = APIReader.getAllCountryCasesByPeriod(status, missingStartDate, date.minusDays(1));
//                    list.addAll(missingMedicao);
//                    missingStartDate = null;
//                }
//                list.add(medicao);
//            }
//        }
//        return list;
//    }
    
    public DataManager.EstatisticaData getMedicaoList(StatusCaso status, LocalDateTime startDate, LocalDateTime endDate){
    	
    	CacheManager cm = new CacheManager();
		
		HashMap<String, Medicao> mapInicialHashMap = cm.readFile(status, startDate);
		HashMap<String, Medicao> mapFinalHashMap = cm.readFile(status, endDate);
		
		return new EstatisticaData(mapInicialHashMap, mapFinalHashMap);
    }
    
    public class EstatisticaData {
    	HashMap<String, Medicao> mapInicialHashMap;
    	HashMap<String, Medicao> mapFinalHashMap;
    	public EstatisticaData(HashMap<String, Medicao> mapInicialHashMap, HashMap<String, Medicao> mapFinalHashMap) {
    		this.mapInicialHashMap = mapInicialHashMap;
    		this.mapFinalHashMap = mapFinalHashMap;
    	}
    }
    
}

