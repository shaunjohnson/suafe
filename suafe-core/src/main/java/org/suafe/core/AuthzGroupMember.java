package org.suafe.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.suafe.core.exceptions.AuthzAlreadyMemberOfGroupException;
import org.suafe.core.exceptions.AuthzNotMemberOfGroupException;

import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;

/**
 * Authz group member object. Instances of this class or its subclasses are
 * eligible to be a member of a group.
 * 
 * @since 2.0
 */
public abstract class AuthzGroupMember implements Comparable<AuthzGroupMember>,
        Serializable {
    /** Logger handle. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(AuthzGroupMember.class);

    /** Serialization ID. */
    private static final long serialVersionUID = -4348242302006857451L;

    /** Collection of groups of which is a member. */
    private final List<AuthzGroup> groups = new ArrayList<AuthzGroup>();

    /** Name of this user */
    protected final String name;

    /**
     * Constructor.
     * 
     * @param name User name
     * @param alias User alias
     */
    protected AuthzGroupMember(final String name) {
        super();

        Preconditions.checkNotNull(name, "Name is null");

        this.name = name;
    }

    /**
     * Adds group to collection of groups.
     * 
     * @param group Group to add to collection
     * @return True if group added
     * @throws AuthzAlreadyMemberOfGroupException If this object is already a
     *         member of the group
     */
    public final boolean addGroup(final AuthzGroup group)
            throws AuthzAlreadyMemberOfGroupException {
        LOGGER.debug("addGroup() entered. group={}", group);

        if (group == null) {
            LOGGER.error("addGroup() group is null");

            throw new NullPointerException("Group is null");
        }

        if (groups.contains(group)) {
            LOGGER.error("addGroup() already a member of group");

            throw new AuthzAlreadyMemberOfGroupException();
        }

        if (groups.add(group)) {
            Collections.sort(groups);

            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Compares this object with the provided AuthzGroupMember object.
     * 
     * @param that AuthzGroupMember to compare
     * @return Returns 0 if members are equal, less than 0 if this member is
     *         less than the other or greater than 0 if this repository is
     *         greater
     */
    @Override
    public int compareTo(final AuthzGroupMember that) {
        return ComparisonChain.start().compare(this.name, that.name).result();
    }

    /**
     * Returns an immutable collection of AuthzGroup objects.
     * 
     * @return Immutable collection of AuthzGroup object.
     */
    public final Collection<AuthzGroup> getGroups() {
        return Collections.unmodifiableCollection(groups);
    }

    /**
     * Gets the user name.
     * 
     * @return User name
     */
    public String getName() {
        return name;
    }

    /**
     * Remove a group from the collection of groups.
     * 
     * @param group Group to remove from the collection
     * @return True if group is removed
     * @throws AuthzNotMemberOfGroupException If this object is not a member of
     *         the provided group
     */
    public final boolean removeGroup(final AuthzGroup group)
            throws AuthzNotMemberOfGroupException {
        LOGGER.debug("removeGroup() entered. group={}", group);

        if (group == null) {
            LOGGER.error("removeGroup() group is null");

            throw new NullPointerException("Group is null");
        }

        if (!groups.contains(group)) {
            LOGGER
                    .error("removeGroup() this object is not a member of the group");

            throw new AuthzNotMemberOfGroupException();
        }

        return groups.remove(group);
    }
}
