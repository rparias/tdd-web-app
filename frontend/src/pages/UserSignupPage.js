import React, { useState } from 'react';

const UserSignupPage = (props) => {
  const [state, setState] = useState({
    displayName: '',
    username: '',
    password: '',
    passwordRepeat: '',
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
    props.actions.postSignup(user);
  };

  return (
    <div>
      <h1>Sign Up</h1>
      <div>
        <input
          type="text"
          placeholder="Your display name"
          name="displayName"
          value={state.displayName}
          onChange={handleOnChange}
        />
        <input
          type="text"
          placeholder="Your username"
          name="username"
          value={state.username}
          onChange={handleOnChange}
        />
        <input
          type="password"
          placeholder="Your password"
          name="password"
          value={state.password}
          onChange={handleOnChange}
        />
        <input
          type="password"
          placeholder="Repeat your password"
          name="passwordRepeat"
          value={state.passwordRepeat}
          onChange={handleOnChange}
        />
        <button onClick={handleOnClickSignUp}>Sign Up</button>
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
