# P
Android 的动态权限申请框架<br/>
完整的请求代码：
	
	//this需要是AppFragmentActivity 或者是Fragment
	P p = new P.Builder(this)
				//需要申请的权限
                .requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE})
				//权限申请结果回调
                .onRequestPermissionCallback(new P.OnRequestPermissionCallback() {
                    @Override
                    public void onRequestPermissionResult(RequestPermissionResult result) {
                        switch (result.getState()) {
                            case RequestPermissionResult.ALL_PERMISSION_PASS:
								//所有权限通过
                                L.logE("ALL_PERMISSION_PASS "
                                        + "\n通过的权限 = " + Arrays.toString(result.getPassPermissions()));
                                break;
                            case RequestPermissionResult.SOME_PERMISSION_PASS:
								//部分权限通过
                                L.logE("SOME_PERMISSION_PASS "
                                        + "\n拒绝的权限 = " + Arrays.toString(result.getDeniedPermissions())
                                        + "\n通过的权限 = " + Arrays.toString(result.getPassPermissions()));
                                break;
                            case RequestPermissionResult.NO_PERMISSION_PASS:
								//没有权限通过
                                L.logE("NO_PERMISSION_PASS "
                                        + "\n拒绝的权限 = " + Arrays.toString(result.getDeniedPermissions()));
                                break;
                        }
                    }
                })
				//执行权限申请
                .show();


如果已经获取P的实例，也可以这样修改需要申请的权限
	
 	 p.replacePermissionsAndShow(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
						});

权限申请结果将会在定义是的onRequestPermissionCallback（）中回调。


----------


P使用弱引用保存APPFragmentActivity或者Fragment实例，通过FragmentManager添加PFragment进行权限申请，权限申请完成后，移除PFragment.

