db.createUser({
  user: 'user123',
  pwd: 'mypassword',
  roles: [
    {
      role: 'readWrite',
      db: 'interview'
    },
  ],
});
