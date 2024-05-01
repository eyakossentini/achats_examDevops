package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.repositories.DetailFournisseurRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.services.FournisseurServiceImpl;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ContextConfiguration(classes = {FournisseurServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class FournisseurTest {
    @MockBean
    private FournisseurRepository fournisseurRepository;
    @Autowired
    private FournisseurServiceImpl fournisseurService;
    @MockBean
    private DetailFournisseur df;
    @MockBean
    private DetailFournisseurRepository detailFournisseurRepository;

   // @Test
public void testaAddFournisseur(){
     Fournisseur f = new Fournisseur();
     df.setDateDebutCollaboration(new Date());
     f.setDetailFournisseur(df);

     when(fournisseurRepository.save(f)).thenReturn(f);

     Fournisseur result = fournisseurService.addFournisseur(f);

     assertNotNull(result);
     assertNotNull(result.getDetailFournisseur());
     verify(fournisseurRepository).save(f);
 }
 //@Test
  void testDeleteFournisseur(){
     doNothing().when(fournisseurRepository).deleteById((Long) any());
    fournisseurService.deleteFournisseur(123L);
     verify(fournisseurRepository).deleteById((Long) any());
 }
}
