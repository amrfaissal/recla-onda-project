// **********************************************************************
//
// Copyright (c) 2003-2009 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

// Ice version 3.3.1

package DQSP.server;

public final class reclamationsSortedListHelper
{
    public static void
    write(IceInternal.BasicStream __os, java.util.List<AnalyseTable> __v)
    {
        if(__v == null)
        {
            __os.writeSize(0);
        }
        else
        {
            __os.writeSize(__v.size());
            for(AnalyseTable __elem : __v)
            {
                __os.writeObject(__elem);
            }
        }
    }

    public static java.util.List<AnalyseTable>
    read(IceInternal.BasicStream __is)
    {
        java.util.List<AnalyseTable> __v;
        __v = new java.util.ArrayList();
        final int __len0 = __is.readSize();
       // __is.startSeq(__len0, 4);
        final String __type0 = AnalyseTable.ice_staticId();
        for(int __i0 = 0; __i0 < __len0; __i0++)
        {
            __v.add(null);
            __is.readObject(new IceInternal.ListPatcher<AnalyseTable>(__v, AnalyseTable.class, __type0, __i0));
           // __is.checkSeq();
           // __is.endElement();
        }
       // __is.endSeq(__len0);
        return __v;
    }
}
