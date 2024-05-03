package tn.esprit.rh.achat;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tn.esprit.rh.achat.controllers.FactureRestController;
import tn.esprit.rh.achat.entities.DetailFacture;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.repositories.DetailFactureRepository;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.services.FactureServiceImpl;

@ContextConfiguration(classes = {FactureServiceImpl.class})
@ExtendWith(MockitoExtension.class)
public class FactureTest {
     @MockBean
    private FactureRepository factureRepository;
    @Autowired
    private FactureServiceImpl factureService;
    @MockBean
    private DetailFacture df;
    @MockBean
    private DetailFactureRepository detailFactureRepository;
    @InjectMocks
    private FactureRestController factureRestController;
    
    @Test
    public void testAddFacture() {
        // Mock data
        Facture facture = new Facture();
        facture.setIdFacture(1L);
        facture.setMontantRemise(100);
        facture.setMontantFacture(500);
        facture.setDateCreationFacture(new Date());
        facture.setDateDerniereModificationFacture(new Date());
        facture.setArchivee(false);

        // Mocking the service method
      
        when(factureRepository.save(facture)).thenReturn(facture);
        // Call the controller method
        Facture result = factureService.addFacture(facture);

        // Verify the result and method invocation
        assertNotNull(result);
        assertNotNull(result.getIdFacture());
        verify(factureService, times(1)).addFacture(any(Facture.class));
    }
}