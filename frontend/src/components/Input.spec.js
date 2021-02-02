import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import Input from './Input';

describe('Layout', () => {
  it('has input item', () => {
    const { container } = render(<Input />);
    const input = container.querySelector('input');
    expect(input).toBeInTheDocument();
  });

  it('displays the label provided in props', () => {
    const { queryByText } = render(<Input label="Test label" />);
    const label = queryByText('Test label');
    expect(label).toBeInTheDocument();
  });

  it('does not display the label when no label provided in props', () => {
    const { container } = render(<Input />);
    const label = container.querySelector('label');
    expect(label).not.toBeInTheDocument();
  });

  it('has text type for input when type is not provided as prop', () => {
    const { container } = render(<Input />);
    const input = container.querySelector('input');
    expect(input.type).toBe('text');
  });

  it('has password type for input when password type provided as prop', () => {
    const { container } = render(<Input type="password" />);
    const input = container.querySelector('input');
    expect(input.type).toBe('password');
  });

  it('displays placeholder when it is provided as prop', () => {
    const { container } = render(<Input placeholder="Test placeholder" />);
    const input = container.querySelector('input');
    expect(input.placeholder).toBe('Test placeholder');
  });

  it('has value for input when it is provided as prop', () => {
    const { container } = render(<Input value="Test value" />);
    const input = container.querySelector('input');
    expect(input.value).toBe('Test value');
  });

  it('has onChange callback when it is provided as prop', () => {
    const onChange = jest.fn();
    const { container } = render(<Input onChange={onChange} />);
    const input = container.querySelector('input');
    fireEvent.change(input, { target: { value: 'new-input' } });
    expect(onChange).toHaveBeenCalledTimes(1);
  });
});
