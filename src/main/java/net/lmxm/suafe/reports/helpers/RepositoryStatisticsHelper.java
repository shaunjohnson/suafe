/*
 * Copyright (c) 2006-2017 by LMXM LLC <suafe@lmxm.net>
 *
 * This file is part of Suafe.
 *
 * Suafe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Suafe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Suafe.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.lmxm.suafe.reports.helpers;

import net.lmxm.suafe.api.beans.Repository;

import javax.annotation.Nonnull;
import java.util.List;

public final class RepositoryStatisticsHelper {
    private final List<Repository> repositories;

    private double avgPaths = -1;

    private int count = -1;

    private int maxPaths = -1;

    private int minPaths = -1;

    public RepositoryStatisticsHelper(@Nonnull final List<Repository> repositories) {
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
