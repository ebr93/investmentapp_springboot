//import _, { union } from 'lodash';
//import orderAPI from './marketAPIFetch.js';
//import intList from './investmentsArray';

// Event Listeners for Dashboard
const dashListeners = (() => {
  const dash = document.querySelector('#main-dashboard');
  if (dash !== null) {

    const stockButtons = document.querySelectorAll('.stock-add-btn');
    const tradedStock = document.querySelector('#traded-stock');

    // stocksBttn.addEventListener('click', () => {
    //   orderAPI.getQuote().then(respone => {
    //     console.log(`Printing Array + EventListener:\n${intList}`);
    //     for (let i = 0; i < intList.length; i++) {
    //       intList[i].printStockCard();
    //     }
    //   });
    // });

    stockButtons.forEach(stockButton => 
      stockButton.addEventListener('click', (e) => {
        console.log('this clicked');
        console.log(e.target.id);
        tradedStock.value = e.target.id;
      })
    ) 
  }
})()

// Event Listeners for Admin
const adminListeners = (() => {
  const admin = document.querySelector('#admin-dashboard');
  if (admin!== null) {
    const editButtons = document.querySelectorAll('.user-email');
    const userEmail = document.querySelector('#user-email');

    const stockButtons = document.querySelectorAll('.stock-edit-btn');
    const tradedStock = document.querySelector('#traded-stock');

    editButtons.forEach(editButton => 
      editButton.addEventListener('click', (e) => {
        console.log('this clicked');
        console.log(e.target.id);
        userEmail.value = e.target.id;
      })
    )
    
    stockButtons.forEach(stockButton =>
      stockButton.addEventListener('click', (e) => {
        console.log('this clicked');
        console.log(e.target.id);
        tradedStock.value = e.target.id;
      })
    )
  }
})()


// Event Listeners for Login Form
const loginForm = (() => {
  const loginVal = document.querySelector('.login-validation');
  // console.log(loginVal);

  if (loginVal !== null) {
    const emailLogin = document.querySelector('#emailInput');
    const passLogin = document.querySelector('#passwordInput');
    const bttnLogin = document.querySelector('#loginBttn');

    const emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    const passRegex = /(?=^.{8,}$)(?=.*\d)(?=.*[!@#$%^&*]+)(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/;

    passLogin.addEventListener('keyup', () => {
      if (passLogin.value === '' || passLogin.value === null) {
        passLogin.className = 'form-control is-invalid';
        passLogin.setCustomValidity('Please enter your password');
      } else if (passRegex.test(passLogin.value) === false) {
        passLogin.className = 'form-control is-invalid';
        passLogin.setCustomValidity('Your password must be at least 8 characters including a lowercase letter, an uppercase letter, a special character and a number');
      } else {
        passLogin.className = 'form-control is-valid';
        passLogin.setCustomValidity('');
      }
      passLogin.reportValidity();
    });

    emailLogin.addEventListener('blur', () => {
      if (emailLogin.value === '' || emailLogin.value === null) {
        emailLogin.className = 'form-control is-invalid';
        emailLogin.setCustomValidity('Please enter your email address');
      } else if (emailRegex.test(emailLogin.value) === false) {
        emailLogin.className = 'form-control is-invalid';
        emailLogin.setCustomValidity('Please use a valid email address\n(e.g. envkt@example.com)');
      } else {
        emailLogin.className = 'form-control is-valid';
        emailLogin.setCustomValidity('');
      }
      emailLogin.reportValidity();
    });

    bttnLogin.addEventListener('click', event => {
      // userLoginValidation() is a function to validate the user input, returns true or false
      if (userLoginValidation(emailLogin.value, passLogin.value)) {

      } else {
        event.preventDefault();
        event.stopPropagation();

        emailLogin.className = 'form-control is-invalid';
        passLogin.className = 'form-control is-invalid';
      }
    });
  }
})()


// Event Listeners for SignUp Form
const signupForm = (() => {
  const signupVal = document.querySelector('.signup-validation');
  if (signupVal !== null) {
    const fName = document.querySelector('#fName');
    const lName = document.querySelector('#lName');
    const email = document.querySelector('#newEmail');
    //const zip = document.querySelector('#zipInput');
    const pass = document.querySelector('#createPassword');
    const confirmPass = document.querySelector('#confirmPassword');
    const bttnSignup = document.querySelector('#signupBttn');

    const nameRegex = /^[a-zA-Z]{2,30}$/;
    const emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    //const zipRegex = /^\d{5}$/;
    const passRegex = /(?=^.{8,}$)(?=.*\d)(?=.*[!@#$%^&*]+)(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/;

    fName.addEventListener('blur', () => {
      if (fName.value === '' || fName.value === null) {

        fName.className = 'form-control is-invalid';
        fName.setCustomValidity('Please enter your first name');
      } else if (nameRegex.test(fName.value) === false) {
        fName.className = 'form-control is-invalid';
        fName.setCustomValidity('Please enter a name between 2 and 30 characters long');
      } else {
        fName.className = 'form-control is-valid';
        fName.setCustomValidity('');
      }
      fName.reportValidity();
    });

    lName.addEventListener('blur', () => {
      if (lName.value === '' || lName.value === null) {
        lName.className = 'form-control is-invalid';
        lName.setCustomValidity('Please enter your last name');
      } else if (nameRegex.test(lName.value) === false) {
        lName.className = 'form-control is-invalid';
        lName.setCustomValidity('Please enter a name between 2 and 30 characters long');
      } else {
        lName.className = 'form-control is-valid';
        lName.setCustomValidity('');
      }
      lName.reportValidity();
    });

    email.addEventListener('blur', () => {
      if (email.value === '' || email.value === null) {
        email.className = 'form-control is-invalid';
        email.setCustomValidity('Please enter your email address');
      } else if (emailRegex.test(email.value) === false) {
        email.className = 'form-control is-invalid';
        email.setCustomValidity('Please use a valid email address\n(e.g. envkt@example.com)');
      } else {
        email.className = 'form-control is-valid';
        email.setCustomValidity('');
      }
      email.reportValidity();
    });

    pass.addEventListener('keyup', () => {
      if (pass.value === '' || pass.value === null) {
        pass.className = 'form-control is-invalid';
        pass.setCustomValidity('Please enter your password');
      } else if (passRegex.test(pass.value) === false) {
        pass.className = 'form-control is-invalid';
        pass.setCustomValidity('Your password must be at least 8 characters including a lowercase letter, an uppercase letter, a special character and a number');
      } else {
        pass.className = 'form-control is-valid';
        pass.setCustomValidity('');
      }
      pass.reportValidity();
    });

    confirmPass.addEventListener('keyup', () => {
      if (confirmPass.value === '' || confirmPass.value === null) {
        confirmPass.className = 'form-control is-invalid';
        confirmPass.setCustomValidity('Please confirm your password');
      } else if (confirmPass.value !== pass.value) {
        confirmPass.className = 'form-control is-invalid';
        confirmPass.setCustomValidity('Passwords do not match\nYour password must be at least 8 characters including a lowercase letter, an uppercase letter, a special character and a number');
      } else {
        confirmPass.className = 'form-control is-valid';
        confirmPass.setCustomValidity('');
      }
      confirmPass.reportValidity();
    });

    bttnSignup.addEventListener('click', event => {
      // INPUT IS NECESARY TO BE VALIDATED
      if (fName.value !== '' && lName.value !== '' && email.value !== '' && pass.value !== '' && confirmPass.value !== '' && pass.value === confirmPass.value) {
        // const newUser = new User(fName.value, lName.value, email.value, zip.value, pass.value);
        // usersDatabase.addUser(newUser);
      } else {
        console.log('else button listener');
        event.preventDefault();
        event.stopPropagation();
      }
    });
  }
})()

