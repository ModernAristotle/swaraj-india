#!/usr/bin/env bash
aws configure
aws s3 sync dist s3://si-member/admin/dist/  --acl public-read  --acl bucket-owner-full-control --metadata Cache-Control=max-age=1296000
aws s3 sync dist s3://si-member/admin/dist/  --acl public-read  --acl bucket-owner-full-control --metadata Cache-Control=max-age=1296000
