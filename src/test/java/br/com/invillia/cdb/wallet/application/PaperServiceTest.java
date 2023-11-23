package br.com.invillia.cdb.wallet.application;

import br.com.invillia.cdb.wallet.domain.Paper;
import br.com.invillia.cdb.wallet.exception.PaperException;
import br.com.invillia.cdb.wallet.persistence.entities.PaperEntity;
import br.com.invillia.cdb.wallet.persistence.repositories.PaperRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaperServiceTest {

    @Mock
    private PaperRepository paperRepository;

    @InjectMocks
    private PaperService paperService;

    private String paperId;

    @BeforeEach
    public void setup() {
        paperId = "paper1";
    }

    @DisplayName("Get paper and throw none paper found exception")
    @Test
    public void getPaperAndThrowNonePaperFoundException() {
        // given a paper id

        // stubbing paper repository behavior
        when(paperRepository.findAll())
                .thenReturn(List.of());

        // when call method get paper
        // then exception will be thrown
        PaperException exception = assertThrows(PaperException.class,
                () -> paperService.getPaper(paperId));

        assertEquals(exception.getMessage(), "Code paper-exception-002: Nenhum papel de CDB encontrado no banco.");
    }

    @DisplayName("Get paper and throw paper not found exception")
    @Test
    public void getPaperAndThrowPaperNotFoundException() {
        // given a paper id

        // stubbing paper repository behavior
        when(paperRepository.findAll())
                .thenReturn(List.of(new PaperEntity("paper2", 1.2)));

        // when call method get paper
        // then exception will be thrown
        PaperException exception = assertThrows(PaperException.class,
                () -> paperService.getPaper(paperId));

        assertEquals(exception.getMessage(), "Code paper-exception-001: Papel de CDB não encontrado no banco.");
    }

    @DisplayName("Get paper successfully when paper list is null")
    @Test
    public void getPaperSuccessfullyWhenPaperListIsNull() {
        // given a paper id

        // stubbing paper repository behavior
        when(paperRepository.findAll())
                .thenReturn(List.of(new PaperEntity("paper1", 1.2)));

        // when call method get paper
        // then paper is returned
        Paper paperFound = paperService.getPaper(paperId);

        assertEquals(paperFound.getId(), "paper1");
        assertEquals(paperFound.getPrice(), 1.2);
    }

}