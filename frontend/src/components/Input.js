import React from 'react';

const Input = (props) => {
  return (
    <div>
      {props.label && <label>{props.label}</label>}
      <input
        type={props.type || 'text'}
        placeholder={props.placeholder}
        value={props.value}
        onChange={props.onChange}
      />
    </div>
  );
};

Input.defaultProps = {
  onChange: () => {},
};

export default Input;
