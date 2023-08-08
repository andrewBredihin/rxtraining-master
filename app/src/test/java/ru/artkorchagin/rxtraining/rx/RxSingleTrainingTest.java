package ru.artkorchagin.rxtraining.rx;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;
import ru.artkorchagin.rxtraining.exceptions.ExpectedException;

import static org.mockito.Mockito.reset;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Arthur Korchagin (artur.korchagin@simbirsoft.com)
 * @since 20.11.18
 */
public class RxSingleTrainingTest {

    private final RxSingleTraining mRxSingleTraining = Mockito.spy(new RxSingleTraining());
    private TestScheduler mTestScheduler;

    @Before
    public void setUp() {
        reset(mRxSingleTraining);
        mTestScheduler = new TestScheduler();
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> mTestScheduler);
    }

    @Test
    public void onlyOneElement_success() {
        TestObserver<Integer> testObserver = mRxSingleTraining
                .onlyOneElement(1)
                .test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValues(1);
    }

    @Test
    public void onlyOneElement_error() {
        TestObserver<Integer> testObserver = mRxSingleTraining
                .onlyOneElement(-1)
                .test();

        testObserver.assertNoValues();
        testObserver.assertError(ExpectedException.class);
    }

    @Test
    public void onlyOneElementOfSequence_success() {
        TestObserver<Integer> testObserver = mRxSingleTraining
                .onlyOneElementOfSequence(Observable.fromArray(1, 2, 3))
                .test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValues(1);
    }

    @Test
    public void onlyOneElementOfSequence_error() {
        TestObserver<Integer> testObserver = mRxSingleTraining
                .onlyOneElementOfSequence(Observable.empty())
                .test();

        testObserver.assertNoValues();
        testObserver.assertError(NoSuchElementException.class);
    }

    @Test
    public void calculateSumOfValues() {
        TestObserver<Integer> testObserver = mRxSingleTraining
                .calculateSumOfValues(Observable.fromArray(1, 2, 3))
                .test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValues(6);
    }

    @Test
    public void collectionOfValues() {
        TestObserver<List<Integer>> testObserver = mRxSingleTraining
                .collectionOfValues(Observable.fromArray(1, 2, 3))
                .test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValues(Arrays.asList(1, 2, 3));
    }

    @Test
    public void allElementsIsPositive_true() {
        TestObserver<Boolean> testObserver = mRxSingleTraining
                .allElementsIsPositive(Observable.fromArray(1, 2, 3))
                .test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValues(true);
    }

    @Test
    public void allElementsIsPositive_false() {
        TestObserver<Boolean> testObserver = mRxSingleTraining
                .allElementsIsPositive(Observable.fromArray(1, 2, -3))
                .test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValues(false);
    }
}