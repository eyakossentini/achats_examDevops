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
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.DetailFournisseurRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;
import tn.esprit.rh.achat.services.FournisseurServiceImpl;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
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
    DetailFournisseurRepository detailFournisseurRepository;
    @MockBean
    ProduitRepository produitRepository;
    @MockBean
    SecteurActiviteRepository secteurActiviteRepository;

    @Test
public void testaAddFournisseur(){
     Fournisseur f = new Fournisseur();
     DetailFournisseur df = new DetailFournisseur();
     df.setDateDebutCollaboration(new Date());
     f.setDetailFournisseur(df);

     when(fournisseurRepository.save(f)).thenReturn(f);

     Fournisseur result = fournisseurService.addFournisseur(f);

     assertNotNull(result);
     assertNotNull(result.getDetailFournisseur());
     verify(fournisseurRepository).save(f);
 }
 @Test
  void testDeleteFournisseur(){
     doNothing().when(fournisseurRepository).deleteById((Long) any());
    fournisseurService.deleteFournisseur(123L);
     verify(fournisseurRepository).deleteById((Long) any());
 }
 @Test
    void testAssignSecteurActiviteToFournisseur(){
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setIdFournisseur(1L);
        fournisseur.setLibelle("test");
        fournisseur.setSecteurActivites(new HashSet<>());
        SecteurActivite secActivite = new SecteurActivite();
        secActivite.setIdSecteurActivite(1L);


     when(fournisseurRepository.findById(1L)).thenReturn(Optional.of(fournisseur));
     when(secteurActiviteRepository.findById(1L)).thenReturn(Optional.of(secActivite));
     fournisseurService.assignSecteurActiviteToFournisseur(1L, 1L);
     verify(fournisseurRepository,times(1)).findById(1L);
     verify(secteurActiviteRepository,times(1)).findById(1L);

     verify(fournisseurRepository, times(1)).save(fournisseur);

     Set<SecteurActivite> secteursActivites = fournisseur.getSecteurActivites();

     assertEquals(1, secteursActivites.size());
    }
}
