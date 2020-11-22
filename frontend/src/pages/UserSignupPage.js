import React, { useState } from 'react';

const UserSignupPage = (props) => {
  const [state, setState] = useState({
    displayName: '',
    username: '',
    password: '',
    passwordRepeat: '',
    pendingApiCall: false,
  });

  const handleOnChange = (e) => {
    setState({
      ...state,
      [e.target.name]: e.target.value,
    });
  };

  const handleOnClickSignUp = () => {
    const user = {
      displayName: state.displayName,
      username: state.username,
      password: state.password,
    };
    setState({ pendingApiCall: true });
    props.actions
      .postSignup(user)
      .then((response) => {
        setState({ pendingApiCall: false });
      })
      .catch((error) => {
        setState({ pendingApiCall: false });
      });
  };

  return (
    <div className="container">
      <h1 className="text-center">Sign Up</h1>
      <div className="col-12 mb-3">
        <label>Display Name</label>
        <input
          className="form-control"
          type="text"
          placeholder="Your display name"
          name="displayName"
          value={state.displayName}
          onChange={handleOnChange}
        />
      </div>
      <div className="col-12 mb-3">
        <label>Username</label>
        <input
          className="form-control"
          type="text"
          placeholder="Your username"
          name="username"
          value={state.username}
          onChange={handleOnChange}
        />
      </div>
      <div className="col-12 mb-3">
        <label>Password</label>
        <input
          className="form-control"
          type="password"
          placeholder="Your password"
          name="password"
          value={state.password}
          onChange={handleOnChange}
        />
      </div>
      <div className="col-12 mb-3">
        <label>Repeat your Password</label>
        <input
          className="form-control"
          type="password"
          placeholder="Repeat your password"
          name="passwordRepeat"
          value={state.passwordRepeat}
          onChange={handleOnChange}
        />
      </div>
      <div className="text-center">
        <button
          className="btn btn-primary"
          onClick={handleOnClickSignUp}
          disabled={state.pendingApiCall}
        >
          {state.pendingApiCall && (
            <div
              className="spinner-border text-light spinner-border-sm mr-sm-1"
              role="status"
            >
              <span className="sr-only">Loading...</span>
            </div>
          )}
          Sign Up
        </button>
      </div>
    </div>
  );
};

UserSignupPage.defaultProps = {
  actions: {
    postSignup: () =>
      new Promise((resolve, reject) => {
        resolve({});
      }),
  },
};

export default UserSignupPage;
