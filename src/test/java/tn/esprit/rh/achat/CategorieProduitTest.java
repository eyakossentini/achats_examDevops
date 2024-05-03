package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.CategorieProduit;
import tn.esprit.rh.achat.repositories.CategorieProduitRepository;
import tn.esprit.rh.achat.services.CategorieProduitServiceImpl;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CategorieProduitServiceImpl.class})
    @ExtendWith(SpringExtension.class)
    public class CategorieProduitTest {
        @MockBean
        private CategorieProduitRepository cr;
        @Autowired
        private CategorieProduitServiceImpl CategorieProduitService;
        @MockBean
        private CategorieProduit cp;


        @Test
         void testAddCategorieProduit(){
            CategorieProduit c = new CategorieProduit();
            c.setLibelleCategorie("test");

            when(cr.save(any())).thenReturn(c);
            CategorieProduit result = CategorieProduitService.addCategorieProduit(c);

            verify(cr,times(1)).save(any());
        }
        @Test
        void testdeleteCategorieProduit (){
            doNothing().when(cr).deleteById((Long) any());
            CategorieProduitService.deleteCategorieProduit(123L);
            verify(cr).deleteById((Long) any());
        }






    }



