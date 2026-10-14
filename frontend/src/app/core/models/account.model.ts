export type AccountType = 'CURRENT' | 'SAVING' | 'CORPORATE';
export type AccountState = 'OPEN' | 'CLOSED';

export interface Account {
  customerId: string;
  accountNumber: string;
  accountBalance: number;
  accountState: AccountState;
  creationDate: string;
  accountType: AccountType;
}

export const ACCOUNT_LIMITS: Record<AccountType, number> = {
  CURRENT: 3000,
  SAVING: 4000,
  CORPORATE: 50000
};
