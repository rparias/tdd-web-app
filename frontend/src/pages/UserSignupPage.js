import React from 'react'

const UserSignupPage = () => {
    return (
        <div>
            <h1>Sign Up</h1>
            <div>
                <input type="text" placeholder="Your display name"/>
                <input type="text" placeholder="Your username"/>
                <input type="password" placeholder="Your password"/>
                <input type="password" placeholder="Repeat your password"/>
                <button>Sign Up</button>
            </div>
        </div>
    )
}

export default UserSignupPage
