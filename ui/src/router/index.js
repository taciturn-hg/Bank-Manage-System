import { createRouter, createWebHistory } from 'vue-router'

const Home = () => import('../views/Home.vue')
const Login = () => import('../views/Login.vue')
const Register = () => import('../views/Register.vue')
const AccountList = () => import('../views/AccountList.vue')
const CreateAccount = () => import('../views/CreateAccount.vue')
const Deposit = () => import('../views/Deposit.vue')
const Withdraw = () => import('../views/Withdraw.vue')
const Transfer = () => import('../views/Transfer.vue')
const TransactionHistory = () => import('../views/TransactionHistory.vue')
const Profile = () => import('../views/Profile.vue')

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'Home', component: Home },
    { path: '/login', name: 'Login', component: Login },
    { path: '/register', name: 'Register', component: Register },
    { path: '/accounts', name: 'AccountList', component: AccountList },
    { path: '/accounts/create', name: 'CreateAccount', component: CreateAccount },
    { path: '/deposit', name: 'Deposit', component: Deposit },
    { path: '/withdraw', name: 'Withdraw', component: Withdraw },
    { path: '/transfer', name: 'Transfer', component: Transfer },
    { path: '/transactions', name: 'TransactionHistory', component: TransactionHistory },
    { path: '/profile', name: 'Profile', component: Profile }
  ]
})

export default router