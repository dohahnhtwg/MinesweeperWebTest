package minesweeper.aview.messages;

import minesweeper.model.IStatistic;

import java.io.Serializable;

/**
 * Created by dohahn on 21.05.2016.
 * Response of a StatisticRequest with the actual statistic
 * On receive the tui will print the statistic
 */
public class StatisticResponse implements Serializable {

    /**
     * Requested statistic
     */
    private IStatistic statistic;

    public StatisticResponse(IStatistic statistic)  {
        this.statistic = statistic;
    }

    public IStatistic getStatistic()    {
        return statistic;
    }
}
