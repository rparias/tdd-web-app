import React, { useState } from 'react';
import Input from '../components/Input';

const UserSignupPage = (props) => {
  const [state, setState] = useState({
    displayName: '',
    username: '',
    password: '',
    passwordRepeat: '',
    pendingApiCall: false,
  });
  const [errors, setErrors] = useState({});
  const [matchPassword, setMatchPassword] = useState(true);

  const handleOnChange = (e) => {
    // importat to get the name and value in this way, if not, tests could fail.
    const {name, value} = e.target;
    setState((prevState) => {
      return { ...prevState, [name]: value };
    });
  };

  const handleOnChangePassword = passwordToCompare => e => {
    const value = e.target.value;
    const arePasswordsTheSame = state[passwordToCompare] === value;
    const matchErrors = { ...errors };
    matchErrors.passwordRepeat = arePasswordsTheSame ? '' : 'Does not match to password';
    setErrors(matchErrors)
    setMatchPassword(arePasswordsTheSame);
    handleOnChange(e);
  }

  const handleOnClickSignUp = () => {
    const user = {
      displayName: state.displayName,
      username: state.username,
      password: state.password,
    };
    setState((prevState) => {
      return { ...prevState, pendingApiCall: true };
    });
    props.actions
      .postSignup(user)
      .then((response) => {
        setState((prevState) => {
          return { ...prevState, pendingApiCall: false };
        });
      })
      .catch((apiError) => {
        let errorsApi = { ...errors };
        if (apiError.response.data && apiError.response.data.validationErrors) {
          errorsApi = { ...apiError.response.data.validationErrors };
        }
        setState((prevState) => {
          return { ...prevState, pendingApiCall: false };
        });
        setErrors(errorsApi);
      });
  };

  return (
    <div className="container">
      <h1 className="text-center">Sign Up</h1>
      <div className="col-12 mb-3">
        <Input
          label="Display Name"
          placeholder="Your display name"
          name="displayName"
          value={state.displayName}
          onChange={handleOnChange}
          hasError={errors.displayName && true}
          error={errors.displayName}
        />
      </div>
      <div className="col-12 mb-3">
        <Input
          label="Username"
          placeholder="Your username"
          name="username"
          value={state.username}
          onChange={handleOnChange}
          hasError={errors.username && true}
          error={errors.username}
        />
      </div>
      <div className="col-12 mb-3">
        <Input
          label="Password"
          type="password"
          placeholder="Your password"
          name="password"
          value={state.password}
          onChange={handleOnChangePassword('passwordRepeat')}
          hasError={errors.password && true}
          error={errors.password}
        />
      </div>
      <div className="col-12 mb-3">
        <Input
          label="Repeat your Password"
          type="password"
          placeholder="Repeat your password"
          name="passwordRepeat"
          value={state.passwordRepeat}
          onChange={handleOnChangePassword('password')}
          hasError={errors.passwordRepeat && true}
          error={errors.passwordRepeat}
        />
      </div>
      <div className="text-center">
        <button
          className="btn btn-primary"
          onClick={handleOnClickSignUp}
          disabled={state.pendingApiCall || !matchPassword}
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
