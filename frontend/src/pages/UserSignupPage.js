import React, { useState } from 'react';

const UserSignupPage = () => {
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
        <button>Sign Up</button>
      </div>
    </div>
  );
};

export default UserSignupPage;
