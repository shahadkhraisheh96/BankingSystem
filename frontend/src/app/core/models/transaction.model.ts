export type TransactionType = 'DEPOSIT' | 'WITHDRAW' | 'TRANSFER';

export interface Transaction {
  id?: string;
  accountNumber: string;
  accountNumberTo?: string;
  amount: number;
  type: string;
  transactionDate?: string;
}

export interface DepositRequest {
  accountNumber: string;
  amount: number;
}

export interface WithdrawRequest {
  accountNumber: string;
  amount: number;
}

export interface TransferRequest {
  accountNumber: string;
  accountNumberTo: string;
  amount: number;
}
