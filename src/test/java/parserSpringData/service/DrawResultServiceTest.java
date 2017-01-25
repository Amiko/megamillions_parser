package parserSpringData.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Created by amiko on 1/10/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class DrawResultServiceTest {

    @Mock
    private DrawResultService drawResultService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void verifyGetDrawResult() throws IOException {

        drawResultService.getDrawResult();

        verify(drawResultService, times(1)).getDrawResult();
    }



}