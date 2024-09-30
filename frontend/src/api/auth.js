
export const login = async (email, password) => {
    const url = '/auth/login';

    const response = await fetch(url, {
        method: 'POST',
        body: JSON.stringify({
            email: email,
            password: password
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    });
    return await response.json();

}

export const register = async (firstname, lastname, email, password, role) => {
    const url = '/auth/signup';

    const response = await fetch(url, {
        method: 'POST',
        body: JSON.stringify({
            firstname: firstname,
            lastname: lastname,
            role: role,
            email: email,
            password: password
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    });
    return await response.json();

}