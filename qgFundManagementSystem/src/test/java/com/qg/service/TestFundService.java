package com.qg.service;

import org.junit.Test;

public class TestFundService {
    @Test
    public void TestFormGave() {
        System.out.println(FundService.getInstance().formGave(1L, 2L, "200"));
    }

    @Test
    public void TestapplyToFormGave() {
        System.out.println(FundService.getInstance().applyToFormGave(1L, 2L, "200", "use for test"));
    }

    @Test
    public void TestformGaveForApplicationApprove() {
        System.out.println(FundService.getInstance().formGaveForApplicationApprove( 2L, 24L, 2L));
    }

    @Test
    public void TestformGaveForApplicationReject() {
        System.out.println(FundService.getInstance().formGaveForApplicationReject( 2L, 25L, 2L));
    }

    @Test
    public void TestapproveOrder() {
        System.out.println(FundService.getInstance().approveOrder(42L));
    }

    @Test
    public void TestRejectOrder() {
        System.out.println(FundService.getInstance().rejectOrder(43L));
    }

    @Test
    public void Test_transferFunds() {
        System.out.println(FundService.getInstance().transferFunds(10005L, 10006L, "100", 13L));
    }
    @Test
    public void Test_transferFunds_group() {
        System.out.println(FundService.getInstance().transferFunds_group(10005L, 10006L, "100", 13L));
    }

}
