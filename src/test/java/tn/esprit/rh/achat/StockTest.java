package tn.esprit.rh.achat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;

@ContextConfiguration(classes = { StockServiceImpl.class })
@ExtendWith(SpringExtension.class)
public class StockTest {

	@MockBean
	private StockRepository stockRepo;

	@Autowired
	private StockServiceImpl stockServiceImpl;

	@Test
	void testRetrieveAllStocks() {
		ArrayList<Stock> stockList = new ArrayList<>();
		when(stockRepo.findAll()).thenReturn(stockList);
		List<Stock> actualRetrieveAllStockResult = stockServiceImpl.retrieveAllStocks();
		assertSame(stockList, actualRetrieveAllStockResult);
		assertTrue(actualRetrieveAllStockResult.isEmpty());
		verify(stockRepo).findAll();
	}

	@Test
	void testAddStock() {
		Stock s = new Stock("stock0", 9, 10);
		when(stockRepo.save(s)).thenReturn(s);
		Stock result = stockServiceImpl.addStock(s);
		assertNotNull(result);
		verify(stockRepo).save(s);
	}

	@Test
	void testDeleteStock() {
		doNothing().when(stockRepo).deleteById((Long) any());
		stockServiceImpl.deleteStock(123L);
		verify(stockRepo).deleteById((Long) any());
	}

	@Test
	void testUpdateStock() {
		Stock s = new Stock("stock 3", 2, 10);
		when(stockRepo.save(s)).thenReturn(s);
		Stock result = stockServiceImpl.updateStock(s);
		assertNotNull(result);
		verify(stockRepo).save(s);
	}
	
	
    @Test
    void testRetrieveStock() {
        Long stockId = 1L;
        Stock s = new Stock("stock 3 ", 2 , 10);
        when(stockRepo.findById(stockId)).thenReturn(Optional.of(s));
        Stock result = stockServiceImpl.retrieveStock(stockId);
        verify(stockRepo).findById(stockId);
        assertEquals(s, result);
    }
    

    @Test
    public void testRetrieveStatusStock() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String msgDate = sdf.format(now);
        Stock stock1 = new Stock("Stock 1", 5, 10 );
        stock1.setLibelleStock("stock 1");
        stock1.setQte(5);
        stock1.setQteMin(10);
        Stock stock2 = new Stock("Stock 2", 8, 20);
        stock2.setLibelleStock("stock 2");
        stock2.setQte(8);
        stock2.setQteMin(20);
        
        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);
        when(stockRepo.retrieveStatusStock()).thenReturn(stocks);
        String result = stockServiceImpl.retrieveStatusStock();
        assertTrue(result.contains(msgDate));
        assertTrue(result.contains(stock1.getLibelleStock()));
        assertEquals(20, stock2.getQteMin());
      }
}
