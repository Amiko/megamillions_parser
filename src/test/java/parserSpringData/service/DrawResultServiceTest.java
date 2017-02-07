package parserSpringData.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mockito.*;
import parserSpringData.entity.DrawResult;
import parserSpringData.entity.PrizeBreakdown;
import parserSpringData.repo.PrizeBreakdownRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Created by amiko on 1/10/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class DrawResultServiceTest {

    private DrawResult drawResult;
    private DrawResultService drawResultService;
    private PrizeBreakdownRepository prizeBreakdownRepository;

    @Before
    public void setUp() throws Exception {

        drawResultService = new DrawResultService();
        Date date = new Date(1485216000000L);
        Integer[] balls = {8,42,54,63,67};
        Integer megaball = 11;
        int megaplier = 4;
        drawResult = new DrawResult(date,balls,megaball,megaplier);
    }


    @Ignore
    @Test
    public void verifyGetDrawResult() throws IOException {

        drawResultService.getDrawResult();

        verify(drawResultService, times(1)).getDrawResult();
    }



}