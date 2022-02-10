# Backend challenge

Thank you very much for investing your time in the recruitment process of ToolTime. We hope that you will find this one
an interesting way to spend a couple of hours.

## Help needed!

We want to create an REST API for invoicing. But so far we have only got a rough API draft and some pseudo code.

Most of our services are built using Spring Boot and Kotlin. So [spring initializr](https://start.spring.io/) might be a
good starting point to bootstrap the initial setup.

Note: It is recommended to use an in-memory database, after all this is just an assessment.

### Features

1. Create an invoice
    - Output: `Invoice`
2. Get an invoice by its ID
    - Output: `Invoice`
3. Get the total amount of the current month by customer ID
    - Output: `Float`

### Type definitions

Our colleague drafted the following type definitions. As he did not know what technology we might use in the end these
are pseudo code. He told us though that properties marked with an exclamation mark (`!`) are required.

```
type Customer {
    id: UUID!
    name: String!
    address: String!
}

type Invoice {
    id: UUID!
    code: String! // human readable invoice code/number
    title: String!
    description: String
    issuedAt: String! // ISO date time
    customer: Customer!
    positions: [InvoicePosition!]!
    totalAmount: Float!
}

type InvoicePosition {
    description: String!
    amount: Float!
}
```

## Good to know

You can use any frameworks you want. You can use any libraries you want. Commit all changes to a Git repository and
follow good commit message practice. Focus on good application structure, not a hacky solution.

It's not about the goal but about the journey, show us what you have got!
