/**
 * @copyright
 * ====================================================================
 * Copyright (c) 2006 Xiaoniu.org.  All rights reserved.
 *
 * This software is licensed as described in the file LICENSE, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://code.google.com/p/suafe/.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For exact contribution history, see the revision
 * history and logs, available at http://code.google.com/p/suafe/.
 * ====================================================================
 * @endcopyright
 */
package org.suafe.core.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.suafe.core.AuthzDocument;
import org.suafe.core.AuthzGroup;
import org.suafe.core.AuthzPath;
import org.suafe.core.AuthzUser;

public final class AuthzFileGeneratorImpl {

	private static final String TEXT_NEW_LINE = System.getProperty("line.separator");

	private final int DEFAULT_MAX_LINE_LENGTH = 80;

	private AuthzDocument document = null;

	public AuthzFileGeneratorImpl(final AuthzDocument document) {
		super();

		this.document = document;
	}

	private String createPrefix(final int length) {
		final StringBuilder builder = new StringBuilder();

		for (int i = 0; i < length; i++) {
			builder.append(" ");
		}

		return builder.toString();
	}

	public String generate(final boolean allowMultipleLine) {
		return generate(allowMultipleLine ? DEFAULT_MAX_LINE_LENGTH : -1);
	}

	public void generate(final File file, final boolean allowMultipleLine) {
		generate(file, allowMultipleLine ? DEFAULT_MAX_LINE_LENGTH : -1);
	}

	public void generate(final File file, final int maxLineLength) {
		PrintWriter output = null;

		try {
			output = new PrintWriter(new BufferedWriter(new FileWriter(file)));

			// Process group definitions
			output.print(generate(maxLineLength));
		}
		catch (final FileNotFoundException fne) {
			throw new RuntimeException("generator.filenotfound");
		}
		catch (final IOException ioe) {
			throw new RuntimeException("generator.error");
		}
		catch (final Exception e) {
			throw new RuntimeException("generator.error");
		}
		finally {
			if (output != null) {
				output.close();
			}
		}
	}

	public String generate(final int maxLineLength) {
		final StringBuilder output = new StringBuilder();

		try {
			output.append("# ");
			// output.append(ResourceUtil.getString("application.fileheader"));
			output.append(TEXT_NEW_LINE);
			output.append(TEXT_NEW_LINE);

			// Process alias definitions
			final StringBuilder aliases = new StringBuilder();

			for (final AuthzUser user : document.getUsers()) {
				if (StringUtils.isBlank(user.getAlias())) {
					continue;
				}

				aliases.append(user.getAlias());
				aliases.append(" = ");
				aliases.append(user.getName());
				aliases.append(TEXT_NEW_LINE);
			}

			if (aliases.length() > 0) {
				output.append("[aliases]");
				output.append(TEXT_NEW_LINE);
				output.append(aliases);

				if (document.getGroups().size() > 0) {
					output.append(TEXT_NEW_LINE);
				}
			}

			// Process group definitions
			output.append("[groups]");
			output.append(TEXT_NEW_LINE);

			Collections.sort(document.getGroups());

			for (final AuthzGroup group : document.getGroups()) {
				output.append(group.getName());
				output.append(" = ");

				final String prefix = createPrefix(group.getName().length() + 3);
				boolean isFirstGroupMember = true;

				if (!group.getGroupMembers().isEmpty()) {
					final Iterator<AuthzGroup> members = group.getGroupMembers().iterator();

					StringBuffer groupLine = new StringBuffer();

					while (members.hasNext()) {
						final AuthzGroup memberGroup = members.next();

						if (maxLineLength > 0 && !isFirstGroupMember
								&& groupLine.length() + memberGroup.getName().length() > maxLineLength) {
							output.append(groupLine);
							output.append(TEXT_NEW_LINE);
							output.append(prefix);

							groupLine = new StringBuffer();
						}

						groupLine.append("@");
						groupLine.append(memberGroup.getName());

						// Add comma if more members exist
						if (members.hasNext()) {
							groupLine.append(", ");
						}

						isFirstGroupMember = false;
					}

					output.append(groupLine);
				}

				if (!group.getUserMembers().isEmpty()) {
					if (!group.getGroupMembers().isEmpty()) {
						output.append(", ");
					}

					final Iterator<AuthzUser> members = group.getUserMembers().iterator();

					StringBuffer userLine = new StringBuffer();

					while (members.hasNext()) {
						final AuthzUser memberUser = members.next();
						final String nameAlias = StringUtils.isBlank(memberUser.getAlias()) ? memberUser.getName()
								: "&" + memberUser.getAlias();

						if (maxLineLength > 0 && !isFirstGroupMember
								&& userLine.length() + nameAlias.length() > maxLineLength) {
							output.append(userLine);
							output.append(TEXT_NEW_LINE);
							output.append(prefix);

							userLine = new StringBuffer();
						}

						userLine.append(nameAlias);

						// Add comma if more members exist
						if (members.hasNext()) {
							userLine.append(", ");
						}

						isFirstGroupMember = false;
					}

					output.append(userLine);
				}

				output.append(TEXT_NEW_LINE);
			}

			if (document.getPaths().size() > 0) {
				output.append(TEXT_NEW_LINE);
			}

			// Process access rules
			for (final AuthzPath path : document.getPaths()) {
				// if (path.getAccessRules().size() == 0) {
				// continue;
				// }

				if (path.getRepository() == null) {
					// Server permissions
					output.append("[");
					output.append(path.getPath());
					output.append("]");
					output.append(TEXT_NEW_LINE);
				}
				else {
					// Path permissions
					output.append("[");
					output.append(path.getRepository().getName());
					output.append(":");
					output.append(path.getPath());
					output.append("]");
					output.append(TEXT_NEW_LINE);
				}

				// for (final AuthzAccessRule rule : path.getAccessRules()) {
				// final AuthzPermissionable permissionable = rule.getPermissionable();
				//
				// if (permissionable instanceof AuthzGroup) {
				// final AuthzGroup group = (AuthzGroup) permissionable;
				//
				// output.append("@");
				// output.append(group.getName());
				// output.append(" = ");
				// output.append(rule.getAccessLevel().getAccessLevelCode());
				// output.append(TEXT_NEW_LINE);
				// }
				// else if (permissionable instanceof AuthzUser) {
				// final AuthzUser user = (AuthzUser) permissionable;
				//
				// if (user.getAlias() == null) {
				// output.append(user.getName());
				// }
				// else {
				// output.append("&");
				// output.append(user.getAlias());
				// }
				//
				// output.append(" = ");
				// output.append(rule.getAccessLevel().getAccessLevelCode());
				// output.append(TEXT_NEW_LINE);
				// }
				// else {
				// throw new RuntimeException("generator.error");
				// }
				// }

				output.append(TEXT_NEW_LINE);
			}
		}
		catch (final Exception e) {
			throw new RuntimeException("generator.error");
		}

		return output.toString();
	}
}
