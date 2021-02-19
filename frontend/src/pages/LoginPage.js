import React, { useState } from 'react';
import Input from '../components/Input';

const LoginPage = () => {
  const [state, setState] = useState({
    username: '',
    password: '',
  });

  const handleOnChange = (e) => {
    const { name, value } = e.target;
    setState((prevState) => {
      return { ...prevState, [name]: value };
    });
  };

  return (
    <div className="container">
      <h1 className="text-center">Login</h1>
      <div className="col-12 mb-3">
        <Input
          label="Username"
          placeholder="Your username"
          name="username"
          value={state.username}
          onChange={handleOnChange}
        />
      </div>
      <div className="col-12 mb-3">
        <Input
          label="Password"
          placeholder="Your password"
          name="password"
          type="password"
          value={state.password}
          onChange={handleOnChange}
        />
      </div>
      <div className="text-center">
        <button className="btn btn-primary">Login</button>
      </div>
    </div>
  );
};

export default LoginPage;
