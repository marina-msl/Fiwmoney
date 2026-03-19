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
const TOKEN = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYTEifQ.opp4jpelCzsRV1kJJpefKqu08eV3PoZKRZR8O188_Xs';

export default function () {
  let res = http.get(`${BASE_URL}/wallet/3`, {
    headers: { Authorization: `Bearer ${TOKEN}` },
  });

  check(res, {
    'status 200': (r) => r.status === 200,
    'response < 500ms': (r) => r.timings.duration < 500,
  });

  sleep(1);
}
