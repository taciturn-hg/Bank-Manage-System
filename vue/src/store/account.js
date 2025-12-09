// src/store/account.js
import { defineStore } from 'pinia'
import { createAccountApi, getBalanceApi, getAccountApi, getMyAccountsApi } from '../api/account'

export const useAccountStore = defineStore('account', {
  state: () => ({
    current: null,
    balance: null,
    myAccounts: []
  }),
  actions: {
    async create(payload, token) {
      const acc = await createAccountApi(payload, token)
      this.current = acc
      return acc
    },
    async fetchBalance(accountNumber) {
      const data = await getBalanceApi(accountNumber)
      this.balance = data
      return data
    },
    async fetchAccount(accountNumber) {
      const acc = await getAccountApi(accountNumber)
      this.current = acc
      return acc
    },
    async fetchMyAccounts() {
      const list = await getMyAccountsApi()
      this.myAccounts = Array.isArray(list) ? list : []
      return this.myAccounts
    }
  }
})