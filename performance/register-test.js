import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 10,
    duration: '30s',
};

export default function () {
    const unique = `${Date.now()}-${Math.floor(Math.random() * 100000)}`;

    const payload = JSON.stringify({
        email: `patient_${unique}@test.com`,
        password: 'Password123!',
        role: 'PATIENT',
        firstName: 'Test',
        lastName: 'Patient',
        gender: 'MALE'
    });

    const res = http.post(
        'http://host.docker.internal:8088/api/auth/register',
        payload,
        {
            headers: {
                'Content-Type': 'application/json'
            }
        }
    );

    check(res, {
        'status is 200/201': (r) => r.status === 200 || r.status === 201,
        'response < 1s': (r) => r.timings.duration < 1000,
    });

    sleep(1);
}