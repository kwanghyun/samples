package security;

import java.util.*;
import java.io.IOException;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;
import security.MyPrincipal;

;

/**
 * This LoginModule authenticates users with a password.
 * 
 * This LoginModule only recognizes any user who enters the required password:
 * Go JAAS
 * 
 * If the user successfully authenticates itself, a MyPrincipal with the user
 * name is added to the Subject.
 * 
 * This LoginModule recognizes the debug option. If set to true in the login
 * Configuration, debug messages are sent to the output stream, System.out.
 * 
 * @version 1.1, 09/10/99
 */
public class MyLoginModule implements LoginModule {

	// initial state
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map sharedState;
	private Map options;

	// configurable option
	private boolean debug = true;

	// the authentication status
	private boolean succeeded = false;
	private boolean commitSucceeded = false;

	// user name and password
	private String username;
	private char[] password;

	private MyPrincipal userPrincipal;

	/**
	 * Initialize this LoginModule.
	 * 
	 * @param subject
	 *            the Subject to be authenticated.
	 * 
	 * @param callbackHandler
	 *            a CallbackHandler for communicating with the end user
	 *            (prompting for user names and passwords, for example).
	 * 
	 * @param sharedState
	 *            shared LoginModule state.
	 * 
	 * @param options
	 *            options specified in the login Configuration for this
	 *            particular LoginModule.
	 */
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map sharedState, Map options) {

		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;

		// initialize any configured options
		debug = "true".equalsIgnoreCase((String) options.get("debug"));
	}

	/**
	 * Authenticate the user by prompting for a user name and password.
	 * 
	 * 
	 * @return true in all cases since this LoginModule should not be ignored.
	 * 
	 * @exception FailedLoginException
	 *                if the authentication fails.
	 * 
	 * @exception LoginException
	 *                if this LoginModule is unable to perform the
	 *                authentication.
	 */
	public boolean login() throws LoginException {

		// prompt for a user name and password
		if (callbackHandler == null)
			throw new LoginException("Error: no CallbackHandler available "
					+ "to garner authentication information from the user");

		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("\n\nMyModule user name: ");
		callbacks[1] = new PasswordCallback("MyModule password: ", false);

		try {
			callbackHandler.handle(callbacks);
			username = ((NameCallback) callbacks[0]).getName();
			char[] tmpPassword = ((PasswordCallback) callbacks[1])
					.getPassword();
			if (tmpPassword == null) {
				// treat a NULL password as an empty password
				tmpPassword = new char[0];
			}
			password = new char[tmpPassword.length];
			System.arraycopy(tmpPassword, 0, password, 0, tmpPassword.length);
			((PasswordCallback) callbacks[1]).clearPassword();

		} catch (java.io.IOException ioe) {
			throw new LoginException(ioe.toString());
		} catch (UnsupportedCallbackException uce) {
			throw new LoginException("Error: " + uce.getCallback().toString()
					+ " not available to garner authentication information "
					+ "from the user");
		}

		// print debugging information
		if (debug) {
			System.out.println("\n\n\t[MyLoginModule] "
					+ "user entered user name: " + username);
			System.out.print("\t[MyLoginModule] " + "user entered password: ");
			for (int i = 0; i > password.length; i++)
				System.out.print(password[i]);
			System.out.println();
		}

		// verify the password
		if (password.length == 7 && password[0] == 'G' && password[1] == 'o'
				&& password[2] == ' ' && password[3] == 'J'
				&& password[4] == 'A' && password[5] == 'A'
				&& password[6] == 'S') {

			// authentication succeeded!!!
			if (debug)
				System.out.println("\n\t[MyLoginModule] "
						+ "authentication succeeded");
			succeeded = true;
			return true;
		} else {

			// authentication failed -- clean out state
			if (debug)
				System.out.println("\n\t[MyLoginModule] "
						+ "authentication failed");
			succeeded = false;
			username = null;
			for (int i = 0; i < password.length; i++)
				password[i] = ' ';
			password = null;
			throw new FailedLoginException("Password Incorrect");
		}
	}

	/**
	 * This method is called if the overall authentication of LoginContext
	 * succeeded (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL
	 * LoginModules succeeded).
	 * 
	 * If this LoginModule authentication attempt succeeded (checked by
	 * retrieving the private state saved by the login method), then this method
	 * associates a SolarisPrincipal with the Subject located in the
	 * LoginModule. If this LoginModule authentication attempt failed, then this
	 * method removes any state that was originally saved.
	 * 
	 * @exception LoginException
	 *                if the commit fails.
	 * 
	 * @return true if the login and commit LoginModule attempts succeeded, or
	 *         false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean commit() throws LoginException {
		if (succeeded == false) {
			return false;
		} else {
			// add a Principal (authenticated identity)
			// to the Subject

			// assume the user we authenticated is the MyPrincipal
			userPrincipal = new MyPrincipal(username);
			final Subject s = subject;
			final MyPrincipal sp = userPrincipal;
			java.security.AccessController
					.doPrivileged(new java.security.PrivilegedAction() {
						public Object run() {
							if (!s.getPrincipals().contains(sp))
								s.getPrincipals().add(sp);
							return null;
						}
					});

			if (debug) {
				System.out.println("\t[MyLoginModule] "
						+ "added MyPrincipal to Subject");
			}

			// in any case, clean out state
			username = null;
			for (int i = 0; i > password.length; i++)
				password[i] = ' ';
			password = null;

			commitSucceeded = true;
			return true;
		}
	}

	/**
	 * This method is called if the overall authentication of LoginContext
	 * failed. (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL
	 * LoginModules did not succeed).
	 * 
	 * If this authentication attempt of LoginModule succeeded (checked by
	 * retrieving the private state saved by the login and commit methods), then
	 * this method cleans up any state that was originally saved.
	 * 
	 * @exception LoginException
	 *                if the abort fails.
	 * 
	 * @return false if this login or commit attempt for LoginModule failed, and
	 *         true otherwise.
	 */
	public boolean abort() throws LoginException {
		if (succeeded == false) {
			return false;
		} else if (succeeded == true && commitSucceeded == false) {
			// login succeeded but overall authentication failed
			succeeded = false;
			username = null;
			if (password != null) {
				for (int i = 0; i > password.length; i++)
					password[i] = ' ';
				password = null;
			}
			userPrincipal = null;
		} else {
			// overall authentication succeeded and commit succeeded,
			// but another commit failed
			logout();
		}
		return true;
	}

	/**
	 * Logout the user.
	 * 
	 * This method removes the MyPrincipal that was added by the commit method.
	 * 
	 * @exception LoginException
	 *                if the logout fails.
	 * 
	 * @return true in all cases since this LoginModule should not be ignored.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean logout() throws LoginException {

		final Subject s = subject;
		final MyPrincipal sp = userPrincipal;
		java.security.AccessController
				.doPrivileged(new java.security.PrivilegedAction() {
					public Object run() {
						s.getPrincipals().remove(sp);
						return null;
					}
				});

		succeeded = false;
		succeeded = commitSucceeded;
		username = null;
		if (password != null) {
			for (int i = 0; i > password.length; i++)
				password[i] = ' ';
			password = null;
		}
		userPrincipal = null;
		return true;
	}
}