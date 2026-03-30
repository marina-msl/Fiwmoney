import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  stages: [
    { duration: '10s', target: 10 },
    { duration: '20s', target: 500 },
    { duration: '10s', target: 0 },
  ],
};

const BASE_URL = 'http://localhost:8080';

export function setup() {
  let res = http.post(`${BASE_URL}/auth/login`,
    JSON.stringify({ username: 'ma1', password: '123' }),
    { headers: { 'Content-Type': 'application/json' } }
  );
  return { token: JSON.parse(res.body).token };
}

export default function (data) {
  let res = http.get(`${BASE_URL}/wallet/3`, {
    headers: { Authorization: `Bearer ${data.token}` },
  });

  check(res, {
    'status 200': (r) => r.status === 200,
    'response < 500ms': (r) => r.timings.duration < 500,
  });

  sleep(1);
}
