#ActivityBack

```
        new ActivityResultRequest(this).startForResult(null, new ActivityResultRequest.Callback() {
            @java.lang.Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {

            }
        });

        new ActivityResultNavigatorFragment().startLoginForResult(this, null)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });
```