package org.xiaoniu.suafe.reports.helpers;

import org.xiaoniu.suafe.beans.Repository;

import java.util.List;

public final class RepositoryStatisticsHelper {

    private double avgPaths = -1;

    private int count = -1;

    private int maxPaths = -1;

    private int minPaths = -1;

    private List<Repository> repositories = null;

    public RepositoryStatisticsHelper(List<Repository> repositories) {
        super();

        this.repositories = repositories;
    }

    public double getAvgPaths() {
        if (avgPaths == -1) {
            avgPaths = 0;

            for (Repository repository : repositories) {
                int size = repository.getPaths().size();

                avgPaths += size;
            }

            avgPaths /= getCount();
        }

        return avgPaths;
    }

    public int getCount() {
        if (count == -1) {
            count = repositories.size();
        }

        return count;
    }

    public int getMaxPaths() {
        if (maxPaths == -1) {
            maxPaths = 0;

            for (Repository repository : repositories) {
                int size = repository.getPaths().size();

                maxPaths = (size > maxPaths) ? size : maxPaths;
            }
        }

        return maxPaths;
    }

    public int getMinPaths() {
        if (minPaths == -1) {
            minPaths = Integer.MAX_VALUE;

            for (Repository repository : repositories) {
                int size = repository.getPaths().size();

                minPaths = (size < minPaths) ? size : minPaths;
            }
        }

        return minPaths;
    }
}
