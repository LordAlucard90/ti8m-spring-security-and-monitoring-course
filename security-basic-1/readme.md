# Security Basic - 1

## Exercise

Create the following in memory users:
- name: `alice`, password: `password-a`, roles: `ADMIN` and `STAFF`
- name: `bob`, password: `password-b`, roles: `STAFF`
- name: `charly`, password: `password-c`, roles: `USER`

Hint: provide a bean for the password encoder.

Security requirements:
- Admin users (alice) can access `/default/admin`
- Staff users (alice, bob) can access `/default/staff`
- Authenticated users can access `/default/authenticated`
- Anyone can access `/default/open`

You know when the exercise is successfully completed when all the tests are green.
